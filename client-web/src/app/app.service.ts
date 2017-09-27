import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';

import { SignupResponse, LoginResponse, UriResponse } from './app.bean';


@Injectable()
export class ConstantService {
    
    http_post_server = "http://localhost:8080";
    http_post_server_api_v1 = this.http_post_server+"/api/v1";
    http_headers = new Headers({ 'Content-Type': 'application/json' });
    http_options = new RequestOptions({ 'headers': this.http_headers });
    http_error_code = "999";

    http_authen_options(username, secretKey) {
        let headers = new Headers({
                'Content-Type': 'application/json',
                'username': username,
                'secretKey': secretKey
            });
        return new RequestOptions({ headers: headers });
    }
    
} 


@Injectable()
export class SharedVariableService {
    private rootScope;

    constructor() {
        let rootScope = localStorage.getItem('root_scope') || '{}';
        this.rootScope = JSON.parse(rootScope);
    }

    getRootScope(): any {
        return this.rootScope;
    }

    setRootScope(rootScope): void {
        rootScope = rootScope || {};
        this.rootScope = rootScope;
        localStorage.setItem('root_scope', JSON.stringify(this.rootScope));
    }
    
}


class AbstarctService {

    protected extractData(res: Response) {
        let body = res.json();
        return body || {};
    }

    protected handleErrorObservable (error: Response | any) {
        console.error(error.message || error);
        return Observable.throw(error.message || error);
    }
}


@Injectable()
export class UserService extends AbstarctService {
 
    constructor(private http:Http, private constantService:ConstantService) { 
        super();
    }

    signUpObservable(params): Observable<SignupResponse> {
        return this.http.post(this.constantService.http_post_server_api_v1+'/user', params, this.constantService.http_options)
		   .map(this.extractData)
		   .catch(this.handleErrorObservable);
    }

    loginObservable(params): Observable<LoginResponse> {
        return this.http.post(this.constantService.http_post_server_api_v1+'/login', params, this.constantService.http_options)
            .map(this.extractData)
            .catch(this.handleErrorObservable);
    }

}

@Injectable()
export class UriService extends AbstarctService {
    
    private api_url:string;

    constructor(private http:Http, private constantService:ConstantService, private sharedVariableService:SharedVariableService) { 
        super();
        this.api_url = this.constantService.http_post_server_api_v1+'/uri';
    }

    generateUriObservable(params): Observable<UriResponse> {
        return this.http.post(this.api_url, params, this.constantService.http_options)
            .map(this.extractData)
            .catch(this.handleErrorObservable);
    }

    getUriListObservable(uRLSearchParams:URLSearchParams): Observable<any> {
        
        let rootScope = this.sharedVariableService.getRootScope();
        let userInfo = rootScope.userInfo || {} ;
        let http_authen_headers = new Headers({
                 'Content-Type': 'application/json'
                 , 'username': userInfo.username
                 , 'secretKey': userInfo.secretKey
            });
        //let params: URLSearchParams = new URLSearchParams();
        let http_authen_options = new RequestOptions({ 
                'headers': http_authen_headers
                , 'search': uRLSearchParams
            });
        return this.http.get(this.api_url, http_authen_options)
            .map(this.extractData)
            .catch(this.handleErrorObservable);

    }

}

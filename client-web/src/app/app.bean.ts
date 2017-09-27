

export class DefaultResponse {
    code:string;
    message:string;
}


export class SignupResponse extends DefaultResponse {
    username:string;
}

export class LoginResponse extends SignupResponse {
    username:string;
    secretKey:string;
}

export class UriResponse extends DefaultResponse {
    uri:string;
    shortUri:string;
}
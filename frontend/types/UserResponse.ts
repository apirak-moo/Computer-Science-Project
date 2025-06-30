export interface UserResponse {
    authenticated: boolean;
    user: User
}

interface User {
    email: string;
    roles: string[]
}
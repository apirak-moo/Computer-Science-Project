import type { ProfessorProfile } from "./ProfessorProfile";

export interface Professor {
    id?: string;
    email: string;
    password: string;
    role: string;
    profile: ProfessorProfile | undefined;
}
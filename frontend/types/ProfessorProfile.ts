import type { ProfessorTitle } from "./ProfessorTitle";

export interface ProfessorProfile {
    id?: number;
    title: ProfessorTitle
    name: string;
    phone: string;
    git: string;
    image?: string;
    major: string;
}
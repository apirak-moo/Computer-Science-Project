import type { ProfessorExpertiseRequest } from "./ProfessorExpertiseRequest";

export interface ProfessorProfileRequest {
    id?: number;
    titleId: number;
    nameTh: string;
    nameEn: string;
    birthday: Date;
    phone: string;
    git: string;
    major: string;
    image: File | undefined
    expertises: ProfessorExpertiseRequest[] | []
}
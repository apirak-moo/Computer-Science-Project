import type { ProfessorEducationalQualificationRequest } from "./ProfessorEducationalQualificationRequest";
import type { ProfessorProfileRequest } from "./ProfessorProfileRequest";
export interface ProfessorRequest {
    id?: string;
    email: string;
    password?: string;
    positions: number[] | [];
    qualifications: ProfessorEducationalQualificationRequest[] | []
    profile: ProfessorProfileRequest;
}
import type { ProfessorExpertise } from "./ProfessorExpertise";
import type { ProfessorTitle } from "./ProfessorTitle";

export interface ProfessorProfile {
    id?: number;
    title: ProfessorTitle
    nameTh: string;
    nameEn: string;
    birthday: Date | undefined;
    phone: string;
    git: string;
    image: string;
    major: string;
    expertises: ProfessorExpertise[] | undefined;
}
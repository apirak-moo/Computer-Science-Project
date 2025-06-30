import type { ProfessorProfile } from "./ProfessorProfile";
import type { ProfessorQualification } from "./ProfessorQualification";
import type { ProfessorResearchWork } from "./ProfessorResearchWork";

export interface Professor {
    id?: string;
    email: string;
    positions: string[] | string;
    profile: ProfessorProfile | undefined;
    qualifications: ProfessorQualification[] | undefined
    work: ProfessorResearchWork[] | []
}
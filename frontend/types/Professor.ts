import type { Position } from "./Position";
import type { ProfessorProfile } from "./ProfessorProfile";
import type { ProfessorQualification } from "./ProfessorQualification";

export interface Professor {
    id?: string;
    email: string;
    positions: Position[] | undefined;
    profile: ProfessorProfile | undefined;
    qualifications: ProfessorQualification[] | undefined
}
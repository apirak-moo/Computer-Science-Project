import type { Degree } from "./Degree";

export interface ProfessorQualification {
    id?: number | null;
    university: string;
    major: string;
    degree?: Degree | undefined
    degreeId?: number | null;
}
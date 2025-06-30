import type { ProfessorResearchWorkImage } from "./ProfessorResearchWorkImage";

export interface ProfessorResearchWork {
    id?: number;
    title: string;
    description: string;
    createAt?: Date;
    updateAt?: Date;
    images?: ProfessorResearchWorkImage[] | []; 
}
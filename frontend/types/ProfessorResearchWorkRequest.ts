export interface ProfessorResearchWorkRequest {
    id?: number;
    professorId: string;
    title: string;
    description: string;
    images: File[] | []
}
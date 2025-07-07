export interface ProfessorProfileRequest {
    id?: number;
    titleId: number | null;
    name: string;
    phone: string;
    git: string;
    major: string;
    image?: File | undefined
}
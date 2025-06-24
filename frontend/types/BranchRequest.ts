export interface BranchRequest {
    id?: number;
    majorId: number;
    nameTh: string;
    nameEn: string;
    tuitionFees: number;
    language: string;
    courses: File | undefined;
    image: File | undefined;
    description: string;
    careers: string;
    status: boolean;
}
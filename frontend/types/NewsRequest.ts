export interface NewsRequest {
    id?: number;
    title: string;
    detail: string;
    linkName: string;
    link: string;
    images: File[] | [];
}
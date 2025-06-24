import type { NewsImage } from "./NewsImage";

export interface News {
    id: number;
    title: string;
    detail: string;
    linkName: string;
    link: string;
    newsImages: NewsImage[]
    createdAt: Date,
    updatedAt: Date
}
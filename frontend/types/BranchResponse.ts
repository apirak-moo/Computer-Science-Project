import type { BranchCourse } from "./BranchCourse";
import type { BranchMajor } from "./BranchMajor";

export interface BranchResponse {
  id?: number;
  major: BranchMajor;
  nameTh: string;
  nameEn: string;
  tuitionFees: number;
  language: string;
  courses: BranchCourse[];
  image: string;
  description: string;
  careers: string;
  status: boolean;
}

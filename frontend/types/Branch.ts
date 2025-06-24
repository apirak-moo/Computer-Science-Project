import type { BranchCourse } from "./BranchCourse";
import type { Major } from "./Major";

export interface Branch {
  id?: number;
  major: Major;
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

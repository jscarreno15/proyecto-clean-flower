import { FormGroup } from "@angular/forms";

export class Utils {
  private static stringIsNotNumber(value: any): boolean {
    return isNaN(Number(value));
  }

  static validationForm( campo:string, formGroup:FormGroup ){
    return formGroup.controls[campo].errors &&
            formGroup.controls[campo].touched
  }
}

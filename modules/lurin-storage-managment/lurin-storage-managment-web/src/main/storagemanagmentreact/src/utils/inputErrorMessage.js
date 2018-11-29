export default function inputErrorMessage(field, errorApiResponse) {
  if (!errorApiResponse) return '';

  const errorMessages = errorApiResponse.errors ?
        errorApiResponse.errors.filter((error) => error.field === field) :
        errorApiResponse.filter((error) => error.field === field);

  if (errorMessages.length >= 1) {
    return errorMessages[0].message;
  }

  return '';
}

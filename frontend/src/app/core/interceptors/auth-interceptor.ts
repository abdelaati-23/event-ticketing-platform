import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // On récupère le token stocké (que l'on définira lors du login)
  const token = localStorage.getItem('jwt_token');

  // Si le token existe, on clone la requête pour y injecter l'en-tête Authorization
  if (token) {
    const clonedRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(clonedRequest);
  }

  // Sinon, on laisse passer la requête telle quelle (ex: pour le login ou l'inscription)
  return next(req);
};

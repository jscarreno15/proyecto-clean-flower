# Usar la imagen base de Node 18
FROM node:18

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app/

# Copiar el código fuente al contenedor
COPY . .

# Instalar Angular CLI y dependencias del proyecto
RUN npm install @angular/cli@15.0.4 -g \
    && npm install --legacy-peer-deps --force \
    && ng config -g cli.warnings.versionMismatch false

# Construir la aplicación Angular
RUN ng build

# Instalar Nginx
RUN apt-get update && apt-get install -y nginx && rm -rf /var/cache/apt/archives /var/lib/apt/lists/* \
    && rm -f /var/log/nginx/access.log && rm -f /var/log/nginx/error.log \
    && ln -s /dev/stderr /var/log/nginx/error.log && ln -s /dev/stdout /var/log/nginx/access.log

# Asegurar que los archivos construidos de Angular se copien al directorio que Nginx espera servir
RUN cp -r dist/clean-flower/* /var/www/html/

# Copiar la configuración personalizada de Nginx al contenedor
COPY nginx.conf /etc/nginx/nginx.conf

# Verificar la configuración de Nginx y recargar el servicio
RUN nginx -t && service nginx reload

# Ejecutar Nginx en primer plano
CMD ["nginx", "-g", "daemon off;"]

# Exponer el puerto 4200
EXPOSE 4200


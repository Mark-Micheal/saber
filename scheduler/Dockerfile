FROM php:7.3-apache-stretch
RUN apt-get update 
RUN apt-get install -y \
    git \
    zip \
    curl \
    openssl \
    unzip \
    libpq-dev

RUN docker-php-ext-install pdo pgsql pdo_pgsql

RUN curl -sS https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin --filename=composer


WORKDIR /app

COPY composer.json /app
COPY composer.lock /app
RUN composer install --no-scripts --no-autoloader

COPY . /app

#RUN composer install

RUN composer dump-autoload --optimize 
#RUN composer update

#&& composer run-script post-install-cmd

RUN php artisan key:generate
#RUN php artisan passport:install
#RUN php artisan migrate

CMD ["php", "artisan", "serve", "--host=0.0.0.0", "--port=8100"]
EXPOSE 8100

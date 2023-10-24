
# Установите рабочую директорию
FROM python:3.11.5-slim

WORKDIR /app
RUN apt-get update && apt-get install -y g++
# Скопируйте код сервиса в контейнер
COPY main.py .
COPY fasttext-0.9.2-cp311-cp311-win_amd64.whl /app/fasttext-0.9.2-cp311-cp311-win_amd64.whl
COPY fasttext-social-network-model.bin /app/models/
COPY fasttext-social-network-model.bin /usr/local/lib/python3.11/site-packages/dostoevsky/data/models/
# Установите зависимости Python
RUN pip install fasttext-wheel
RUN pip install dostoevsky
RUN pip install Flask
#RUN pip install -r requirements.txt

# Определите порт, если он нужен
EXPOSE 5000

# Запустите Python-сервис
CMD ["python", "main.py"]

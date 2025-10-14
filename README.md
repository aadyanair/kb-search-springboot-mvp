KB Search Spring Boot MVP

A Spring Boot application for ingesting PDFs, generating embeddings (dummy or real), and querying a knowledge base.

* * * * *

API Endpoints

1.  Ingest PDF

URL: /api/ingest\
Method: POST\
Form Data: file (PDF file)

Response example:\
{\
"message": "PDF Ingested Successfully: 7 chunks created."\
}

1.  Query Knowledge Base

URL: /api/query\
Method: POST\
Body example:\
{\
"query": "Your question here"\
}

Response example:\
{\
"response": "Answer: em using YOLOv5 deployed on Raspberry Pi for smart agriculture..."\
}

* * * * *

Project Structure

src/main/java/com/kbsearch\
├─ controller # REST API controllers\
├─ model # Data models (Chunk)\
├─ service # PDF extraction, embedding, retrieval services\
└─ Application.java

* * * * *

Setup Instructions

1.  Set Hugging Face API token (optional, if using real embeddings)

Linux / Mac:\
export HF_API_TOKEN="your_huggingface_api_token"

Windows:\
setx HF_API_TOKEN "your_huggingface_api_token"

1.  Build the project using Maven

mvn clean install

1.  Run the Spring Boot application

mvn spring-boot:run

* * * * *

Notes

-   Currently uses dummy embeddings; replace EmbeddingService logic with a real embedding model if needed.

-   All API keys and secrets must be stored as environment variables. Do not commit secrets to GitHub.

-   Chunks are stored in-memory; restarting the app clears all ingested documents.

* * * * *

License

This project is open-source and free to use under the MIT License.

* * * * *

Author

Aadya Nair\
BTech CSE Student, VIT-AP

* * * * *



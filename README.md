# Farmcheck - prototip ui branch
This branch's purpose is to showcase a prototype of the ui

## Launching the JHipster application
* Intall & run Elasticsearch
	1. download Elasticsearch binaries from: [https://www.elastic.co/downloads/elasticsearch](here)
	2. configure Elasticsearch to work with the application (see "Configuring Elasticsearch")
	3. start by running the Electricsearch script inside the bin directory
* Run the "mvnw" script (maven script)
* Start the frontend by running "npm start" (if you don't have Node Package Manager *(npm)* installed use the "npmw" script provieded inside the main directory)

## Configure Elasticsearch
The Elasticsearch is configured using the "config/elasticsearch.yml" file (relative to the Elasticsearch installation)
* change the "xpack.security.enabled" property to false
* change the "http.host" property to "127.0.0.1"

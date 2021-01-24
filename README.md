# file-rank

## Overview

File-rank is a simple indexing and search engine of text files.

Given a directory containing a set of text files, file-rank will:

- **Index** the data. Current implementation builds a HashMap containing information of word occurrences in the set of files provided
- Open a **search** console in which the user can enter a few words to search.
- Return a **ranking** result of the files in the given directory. Current implementation is a simple implementation in which rank of file F is given by (X/N)*100, where N is the total number of distinct words the user entered and X is the number of these N words found in file F.

## Prerequisites

- JDK 1.7
- Maven, tested with 3.2.3

## Quickstart

Clone the repository to your working directory:\
`git clone "https://github.com/paufq/file-rank.git"`

Build the jar (currently there are no tests):\
`cd file-rank`
`mvn clean install -DskipTests`

Run the program replacing the <input_directory> argument:\
`java -jar .\target\file-rank-0.0.1-SNAPSHOT-jar-with-dependencies.jar <input_directory>`

When asked, enter a few words to rank the files in `<input_directory>`

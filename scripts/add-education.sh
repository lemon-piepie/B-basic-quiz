#!/bin/bash

USER_ID=${1:?USER_ID is required!}

curl -H "Content-Type: application/json" --data @scripts/education.json localhost:8080/users/${USER_ID}/educations


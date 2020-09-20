#!/bin/bash

USER_ID=${1:?USER_ID is required!}

curl --silent localhost:8080/users/${USER_ID}/educations | json_pp


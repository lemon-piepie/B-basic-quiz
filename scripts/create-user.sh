#!/bin/bash

curl -H "Content-Type: application/json" --data @scripts/user.json localhost:8080/users


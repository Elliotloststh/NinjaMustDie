#! /bin/bash
set -u

# service-register
docker run -id --rm --name ren3 --net=host -p 10000:10000 -t ren3 --spring.profiles.active=dev




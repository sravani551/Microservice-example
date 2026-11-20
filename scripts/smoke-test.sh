#!/usr/bin/env bash
# End-to-end check through the gateway once services have registered with Eureka.
set -euo pipefail
GATEWAY=${GATEWAY:-http://localhost:9000}

for i in $(seq 1 60); do
  if body=$(curl -fsS "$GATEWAY/users/1" 2>/dev/null) && echo "$body" | grep -q '"degraded":false'; then
    echo "Gateway -> user-service -> post/notification services OK"
    echo "$body"
    curl -fsS "$GATEWAY/posts/7" && echo
    curl -fsS "$GATEWAY/notifications/5" && echo
    exit 0
  fi
  echo "waiting for service registration ($i/60)..."
  sleep 5
done
echo "Smoke test failed" >&2
exit 1

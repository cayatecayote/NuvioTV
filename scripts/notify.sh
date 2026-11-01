#!/usr/bin/env bash
# Usage: notify.sh "message"   (needs TELEGRAM_BOT_TOKEN and TELEGRAM_CHAT_ID; never fails the job)
set -u
if [[ -z "${TELEGRAM_BOT_TOKEN:-}" || -z "${TELEGRAM_CHAT_ID:-}" ]]; then
  echo "Telegram not configured, skipping notification." >&2
  exit 0
fi
curl -sS --max-time 20 -o /dev/null \
  "https://api.telegram.org/bot${TELEGRAM_BOT_TOKEN}/sendMessage" \
  --data-urlencode "chat_id=${TELEGRAM_CHAT_ID}" \
  --data-urlencode "text=$1" \
  --data-urlencode "disable_web_page_preview=true" \
  || echo "Telegram notification failed." >&2
exit 0

#!/bin/sh
set -eu
mkdir -p /tmp/wallet
cp -a /wallet/. /tmp/wallet/
cat > /tmp/wallet/sqlnet.ora << 'EOF'
WALLET_LOCATION = (SOURCE = (METHOD = file) (METHOD_DATA = (DIRECTORY="/tmp/wallet")))
SSL_SERVER_DN_MATCH=yes
EOF
export TNS_ADMIN=/tmp/wallet
export ORACLE_TNS_ADMIN=/tmp/wallet
exec java -jar /app/app.jar

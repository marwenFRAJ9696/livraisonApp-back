#!/bin/bash

# Informations de connexion MySQL
MYSQL_USER="root"
MYSQL_PASSWORD=""
MYSQL_DATABASE="fly_delivery"

# Emplacement de sauvegarde
BACKUP_DIR="/root/project/backup"

# Créez le nom de fichier avec la date
BACKUP_FILE="$BACKUP_DIR/backup_$(date +'%Y%m%d_%H%M%S').sql"

# Utilisez mysqldump pour exporter la base de données
docker exec -i back_mysql-db_1  mysqldump -u$MYSQL_USER -p$MYSQL_PASSWORD $MYSQL_DATABASE > $BACKUP_FILE

# Vérifiez si la sauvegarde a réussi
if [ $? -eq 0 ]; then
    echo "Sauvegarde réussie : $BACKUP_FILE"
else
    echo "Échec de la sauvegarde"
fi

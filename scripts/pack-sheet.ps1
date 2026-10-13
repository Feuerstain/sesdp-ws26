# Zips labs/sheet-NN for the OLAT upload into dist/sheet-NN.zip.
# Excludes the assignment PDF, build output and IDE files.
# Usage: .\scripts\pack-sheet.ps1 1
param([Parameter(Mandatory)][int]$Number)

$root = Split-Path -Parent $PSScriptRoot
$name = "sheet-{0:D2}" -f $Number
$source = Join-Path $root "labs\$name"
if (-not (Test-Path $source)) { Write-Error "$source not found"; exit 1 }

$dist = Join-Path $root "dist"
New-Item -ItemType Directory -Force $dist | Out-Null
$staging = Join-Path $dist $name
if (Test-Path $staging) { Remove-Item -Recurse -Force $staging }

robocopy $source $staging /E /XD target .idea .vscode out bin /XF "sheet_*.pdf" "*.iml" ".gitkeep" | Out-Null

$zip = Join-Path $dist "$name.zip"
if (Test-Path $zip) { Remove-Item -Force $zip }
Compress-Archive -Path (Join-Path $staging "*") -DestinationPath $zip
Remove-Item -Recurse -Force $staging

Write-Host "Created $zip"

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

robocopy $source $staging /E /XD target .idea .vscode out bin obj .vs /XF "sheet_*.pdf" "*.iml" ".gitkeep" | Out-Null

$zip = Join-Path $dist "$name.zip"
if (Test-Path $zip) { Remove-Item -Force $zip }
# tar writes forward slashes; Compress-Archive (PowerShell 5.1) writes backslashes,
# which breaks extraction on macOS/Linux
& "$env:SystemRoot\System32\tar.exe" -a -c -f $zip -C $staging .
if ($LASTEXITCODE -ne 0) { Write-Error "tar failed"; exit 1 }
Remove-Item -Recurse -Force $staging

Write-Host "Created $zip"

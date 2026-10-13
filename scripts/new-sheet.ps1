# Creates labs/sheet-NN from labs/_template.
# Usage: .\scripts\new-sheet.ps1 2
param([Parameter(Mandatory)][int]$Number)

$root = Split-Path -Parent $PSScriptRoot
$name = "sheet-{0:D2}" -f $Number
$target = Join-Path $root "labs\$name"

if (Test-Path $target) { Write-Error "$target already exists"; exit 1 }

Copy-Item -Recurse (Join-Path $root "labs\_template") $target
$pom = Join-Path $target "pom.xml"
(Get-Content $pom -Raw).Replace("<artifactId>sheet-XX</artifactId>", "<artifactId>$name</artifactId>") |
    Set-Content $pom -Encoding utf8 -NoNewline

Write-Host "Created $target - put the sheet PDF in there and fill in README.md"

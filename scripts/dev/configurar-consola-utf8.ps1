chcp 65001 | Out-Null

[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
[Console]::InputEncoding = [System.Text.Encoding]::UTF8
$OutputEncoding = [System.Text.Encoding]::UTF8

Write-Host "Consola configurada en UTF-8 correctamente." -ForegroundColor Green

$BaseUrl = "http://localhost:8080"
$Fecha = Get-Date -Format "yyyyMMdd_HHmmss"
$EvidenciasDir = ".\docs\evidencias"
$EvidenciaFile = "$EvidenciasDir\HU-003-HU-004_$Fecha.json"

New-Item -ItemType Directory -Force -Path $EvidenciasDir | Out-Null

$resultados = @()

function Capturar-Error {
    param (
        [string]$Nombre,
        $ErrorObj,
        $Request = $null
    )

    $statusCode = $null
    $responseJson = $null

    try {
        $statusCode = $ErrorObj.Exception.Response.StatusCode.value__
        $reader = New-Object System.IO.StreamReader($ErrorObj.Exception.Response.GetResponseStream())
        $responseBody = $reader.ReadToEnd()

        if ($responseBody) {
            $responseJson = $responseBody | ConvertFrom-Json
        }
    }
    catch {
        $responseJson = @{
            error = $ErrorObj.Exception.Message
        }
    }

    return [PSCustomObject]@{
        caso = $Nombre
        exitoso = $false
        status = $statusCode
        request = $Request
        respuesta = $responseJson
    }
}

function Ejecutar-Get {
    param (
        [string]$Nombre,
        [string]$Url
    )

    Write-Host "Ejecutando:" $Nombre -ForegroundColor Cyan

    try {
        $respuesta = Invoke-RestMethod -Method GET -Uri $Url
        return [PSCustomObject]@{
            caso = $Nombre
            exitoso = $true
            status = 200
            respuesta = $respuesta
        }
    }
    catch {
        return Capturar-Error -Nombre $Nombre -ErrorObj $_
    }
}

function Ejecutar-Json {
    param (
        [string]$Nombre,
        [string]$Metodo,
        [string]$Url,
        [hashtable]$Body
    )

    Write-Host "Ejecutando:" $Nombre -ForegroundColor Cyan

    try {
        $jsonBody = $Body | ConvertTo-Json
        $respuesta = Invoke-RestMethod `
            -Method $Metodo `
            -Uri $Url `
            -ContentType "application/json" `
            -Body $jsonBody

        return [PSCustomObject]@{
            caso = $Nombre
            exitoso = $true
            status = 200
            request = $Body
            respuesta = $respuesta
        }
    }
    catch {
        return Capturar-Error -Nombre $Nombre -ErrorObj $_ -Request $Body
    }
}

$resultados += Ejecutar-Get `
    -Nombre "HU-003 - Consultar configuración JWT" `
    -Url "$BaseUrl/api/v1/admin/configuracion/seguridad-jwt"

$resultados += Ejecutar-Json `
    -Nombre "HU-003 - Actualizar configuración JWT válida" `
    -Metodo "PUT" `
    -Url "$BaseUrl/api/v1/admin/configuracion/seguridad-jwt" `
    -Body @{
        accessTokenHoras = 12
        refreshTokenDias = 30
        intentosFallidosMax = 5
        bloqueoMinutos = 20
    }

$resultados += Ejecutar-Json `
    -Nombre "HU-003 - Error access token mayor a 24" `
    -Metodo "PUT" `
    -Url "$BaseUrl/api/v1/admin/configuracion/seguridad-jwt" `
    -Body @{
        accessTokenHoras = 25
        refreshTokenDias = 30
        intentosFallidosMax = 5
        bloqueoMinutos = 20
    }

$resultados += Ejecutar-Json `
    -Nombre "HU-003 - Error refresh token mayor a 90" `
    -Metodo "PUT" `
    -Url "$BaseUrl/api/v1/admin/configuracion/seguridad-jwt" `
    -Body @{
        accessTokenHoras = 12
        refreshTokenDias = 91
        intentosFallidosMax = 5
        bloqueoMinutos = 20
    }

$resultados += Ejecutar-Get `
    -Nombre "HU-004 - Listar feriados" `
    -Url "$BaseUrl/api/v1/admin/feriados"

$resultados += Ejecutar-Get `
    -Nombre "HU-004 - Listar feriados activos" `
    -Url "$BaseUrl/api/v1/admin/feriados?soloActivos=true"

$resultados += Ejecutar-Json `
    -Nombre "HU-004 - Crear feriado válido" `
    -Metodo "POST" `
    -Url "$BaseUrl/api/v1/admin/feriados" `
    -Body @{
        nombre = "Día de prueba institucional"
        fecha = "2026-07-15"
        tipo = "NACIONAL"
        recurrenteAnual = $false
    }

$resultados += Ejecutar-Json `
    -Nombre "HU-004 - Error feriado duplicado Inti Raymi" `
    -Metodo "POST" `
    -Url "$BaseUrl/api/v1/admin/feriados" `
    -Body @{
        nombre = "Inti Raymi duplicado"
        fecha = "2026-06-24"
        tipo = "REGIONAL_CUSCO"
        recurrenteAnual = $true
    }

$resultados += Ejecutar-Json `
    -Nombre "HU-004 - Desactivar feriado ID 1" `
    -Metodo "PATCH" `
    -Url "$BaseUrl/api/v1/admin/feriados/1/estado" `
    -Body @{
        activo = $false
    }

$resultados += Ejecutar-Json `
    -Nombre "HU-004 - Activar feriado ID 1" `
    -Metodo "PATCH" `
    -Url "$BaseUrl/api/v1/admin/feriados/1/estado" `
    -Body @{
        activo = $true
    }

$resultados | ConvertTo-Json -Depth 20 | Out-File -FilePath $EvidenciaFile -Encoding UTF8

Write-Host ""
Write-Host "Pruebas finalizadas." -ForegroundColor Green
Write-Host "Evidencia generada en:" $EvidenciaFile -ForegroundColor Yellow

$resultados | ConvertTo-Json -Depth 20

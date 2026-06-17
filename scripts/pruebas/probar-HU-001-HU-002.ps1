$BaseUrl = "http://localhost:8080"
$Fecha = Get-Date -Format "yyyyMMdd_HHmmss"
$EvidenciasDir = ".\docs\evidencias"
$EvidenciaFile = "$EvidenciasDir\HU-001-HU-002_$Fecha.json"

New-Item -ItemType Directory -Force -Path $EvidenciasDir | Out-Null

$resultados = @()

function Ejecutar-Get {
    param (
        [string]$Nombre,
        [string]$Url
    )

    Write-Host "Ejecutando:" $Nombre -ForegroundColor Cyan

    try {
        $respuesta = Invoke-RestMethod -Method GET -Uri $Url
        $resultado = [PSCustomObject]@{
            caso = $Nombre
            exitoso = $true
            status = 200
            respuesta = $respuesta
        }
        return $resultado
    }
    catch {
        return Capturar-Error -Nombre $Nombre -ErrorObj $_
    }
}

function Ejecutar-Put {
    param (
        [string]$Nombre,
        [string]$Url,
        [hashtable]$Body
    )

    Write-Host "Ejecutando:" $Nombre -ForegroundColor Cyan

    try {
        $jsonBody = $Body | ConvertTo-Json
        $respuesta = Invoke-RestMethod `
            -Method PUT `
            -Uri $Url `
            -ContentType "application/json" `
            -Body $jsonBody

        $resultado = [PSCustomObject]@{
            caso = $Nombre
            exitoso = $true
            status = 200
            request = $Body
            respuesta = $respuesta
        }
        return $resultado
    }
    catch {
        return Capturar-Error -Nombre $Nombre -ErrorObj $_ -Request $Body
    }
}

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

    $resultado = [PSCustomObject]@{
        caso = $Nombre
        exitoso = $false
        status = $statusCode
        request = $Request
        respuesta = $responseJson
    }

    return $resultado
}

$resultados += Ejecutar-Get `
    -Nombre "HU-001/HU-002 - Consultar configuración inicial" `
    -Url "$BaseUrl/api/v1/admin/configuracion/documental"

$resultados += Ejecutar-Put `
    -Nombre "HU-001/HU-002 - Actualizar configuración válida" `
    -Url "$BaseUrl/api/v1/admin/configuracion/documental" `
    -Body @{
        periodoRetencionAnios = 7
        clasificacionPorDefecto = "PUBLICO"
    }

$resultados += Ejecutar-Put `
    -Nombre "HU-001 - Error periodo menor a 1" `
    -Url "$BaseUrl/api/v1/admin/configuracion/documental" `
    -Body @{
        periodoRetencionAnios = 0
        clasificacionPorDefecto = "PUBLICO"
    }

$resultados += Ejecutar-Put `
    -Nombre "HU-002 - Error clasificación RESTRINGIDO no permitida" `
    -Url "$BaseUrl/api/v1/admin/configuracion/documental" `
    -Body @{
        periodoRetencionAnios = 5
        clasificacionPorDefecto = "RESTRINGIDO"
    }

$resultados | ConvertTo-Json -Depth 20 | Out-File -FilePath $EvidenciaFile -Encoding UTF8

Write-Host ""
Write-Host "Pruebas finalizadas." -ForegroundColor Green
Write-Host "Evidencia generada en:" $EvidenciaFile -ForegroundColor Yellow

$resultados | ConvertTo-Json -Depth 20

$BaseUrl = "http://localhost:8080"
$Fecha = Get-Date -Format "yyyyMMdd_HHmmss"
$Salida = "docs/evidencias/HU-005-HU-006_$Fecha.json"

$resultados = @()

function Ejecutar-Caso {
    param(
        [string]$Caso,
        [string]$Metodo,
        [string]$Url,
        [object]$Body = $null,
        [int]$StatusEsperado = 200
    )

    try {
        $params = @{
            Method = $Metodo
            Uri = $Url
            Headers = @{ "Accept" = "application/json" }
        }

        if ($Body -ne $null) {
            $params["ContentType"] = "application/json"
            $params["Body"] = ($Body | ConvertTo-Json -Depth 10)
        }

        $respuesta = Invoke-RestMethod @params

        $script:resultados += [PSCustomObject]@{
            caso = $Caso
            exitoso = ($StatusEsperado -eq 200)
            statusEsperado = $StatusEsperado
            statusObtenido = 200
            request = $Body
            respuesta = $respuesta
        }
    }
    catch {
        $statusCode = $null
        $errorBody = $null

        if ($_.Exception.Response) {
            $statusCode = [int]$_.Exception.Response.StatusCode

            try {
                $stream = $_.Exception.Response.GetResponseStream()
                $reader = New-Object System.IO.StreamReader($stream)
                $raw = $reader.ReadToEnd()

                if ($raw) {
                    $errorBody = $raw | ConvertFrom-Json
                } else {
                    $errorBody = $_.Exception.Message
                }
            }
            catch {
                $errorBody = $_.Exception.Message
            }
        } else {
            $errorBody = $_.Exception.Message
        }

        $script:resultados += [PSCustomObject]@{
            caso = $Caso
            exitoso = ($statusCode -eq $StatusEsperado)
            statusEsperado = $StatusEsperado
            statusObtenido = $statusCode
            request = $Body
            respuesta = $errorBody
        }
    }
}

Ejecutar-Caso `
    -Caso "HU-005 - Listar límites EC" `
    -Metodo "GET" `
    -Url "$BaseUrl/api/v1/admin/configuracion/limites-ec" `
    -StatusEsperado 200

Ejecutar-Caso `
    -Caso "HU-005 - Actualizar límite EXPEDIENTE válido" `
    -Metodo "PUT" `
    -Url "$BaseUrl/api/v1/admin/configuracion/limites-ec/EXPEDIENTE" `
    -Body @{ limiteGb = 25 } `
    -StatusEsperado 200

Ejecutar-Caso `
    -Caso "HU-005 - Límite inválido mayor a 100 GB" `
    -Metodo "PUT" `
    -Url "$BaseUrl/api/v1/admin/configuracion/limites-ec/PLAN" `
    -Body @{ limiteGb = 101 } `
    -StatusEsperado 400

Ejecutar-Caso `
    -Caso "HU-006 - Listar perfiles RBAC" `
    -Metodo "GET" `
    -Url "$BaseUrl/api/v1/admin/perfiles" `
    -StatusEsperado 200

Ejecutar-Caso `
    -Caso "HU-006 - Listar usuarios" `
    -Metodo "GET" `
    -Url "$BaseUrl/api/v1/admin/usuarios" `
    -StatusEsperado 200

Ejecutar-Caso `
    -Caso "HU-006 - Crear usuario válido" `
    -Metodo "POST" `
    -Url "$BaseUrl/api/v1/admin/usuarios" `
    -Body @{
        nombres = "Juan"
        apellidos = "Puma Quispe"
        correo = "juan.puma@regioncusco.gob.pe"
        cargo = "Especialista Documental"
        unidad = "Gerencia Regional de Planeamiento"
        perfil = "ESPECIALISTA_RESPONSABLE"
    } `
    -StatusEsperado 200

Ejecutar-Caso `
    -Caso "HU-006 - Crear usuario duplicado" `
    -Metodo "POST" `
    -Url "$BaseUrl/api/v1/admin/usuarios" `
    -Body @{
        nombres = "Juan"
        apellidos = "Puma Quispe"
        correo = "juan.puma@regioncusco.gob.pe"
        cargo = "Especialista Documental"
        unidad = "Gerencia Regional de Planeamiento"
        perfil = "ESPECIALISTA_RESPONSABLE"
    } `
    -StatusEsperado 409

Ejecutar-Caso `
    -Caso "HU-006 - Obtener usuario ID 1" `
    -Metodo "GET" `
    -Url "$BaseUrl/api/v1/admin/usuarios/1" `
    -StatusEsperado 200

Ejecutar-Caso `
    -Caso "HU-006 - Activar usuario ID 1" `
    -Metodo "PATCH" `
    -Url "$BaseUrl/api/v1/admin/usuarios/1/estado" `
    -Body @{ activo = $true } `
    -StatusEsperado 200

$resultados | ConvertTo-Json -Depth 20 | Out-File -FilePath $Salida -Encoding UTF8

$resumen = [PSCustomObject]@{
    total = $resultados.Count
    exitosos = ($resultados | Where-Object { $_.exitoso -eq $true }).Count
    fallidos = ($resultados | Where-Object { $_.exitoso -eq $false }).Count
    archivo = $Salida
}

Write-Host "Evidencia generada en: $Salida"
Write-Host "Total:" $resumen.total
Write-Host "Exitosos:" $resumen.exitosos
Write-Host "Fallidos:" $resumen.fallidos

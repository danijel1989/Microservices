$ErrorActionPreference = "Stop"

# Move to the directory containing this script
Set-Location $PSScriptRoot

Write-Host "Pulling latest Docker images from GHCR..." -ForegroundColor Cyan
docker compose pull

if ($LASTEXITCODE -ne 0) {
    throw "Docker image pull failed. Deployment stopped."
}

Write-Host "Updating microservices..." -ForegroundColor Cyan
docker compose up -d --remove-orphans

if ($LASTEXITCODE -ne 0) {
    throw "Docker Compose deployment failed."
}

Write-Host "Deployment command completed." -ForegroundColor Green
docker compose ps
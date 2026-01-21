Write-Host "Starting Stadium Management System..." -ForegroundColor Green

$root = Get-Location
$mvnw = Join-Path $root "mvnw.cmd"

# 1. Start Event Service (Java)
Write-Host "Starting Event Service (Java)..." -ForegroundColor Cyan
$eventServicePath = Join-Path $root "event-service"

# We use Start-Process with cmd /k to keep the window open and visible
# We use the absolute path to mvnw.cmd
Start-Process cmd -ArgumentList "/k", "title Java Event Service && cd /d ""$eventServicePath"" && ""$mvnw"" spring-boot:run"

# 2. Wait a bit
Start-Sleep -Seconds 5

# 3. Start Smart Gateway (Python)
Write-Host "Starting Smart Gateway (Python)..." -ForegroundColor Cyan
$gatewayPath = Join-Path $root "smart-gateway"

# Start Python in another window
Start-Process cmd -ArgumentList "/k", "title Python Smart Gateway && cd /d ""$gatewayPath"" && python -m uvicorn main:app --port 8005 --reload"

Write-Host "--------------------------------------------------------" -ForegroundColor Yellow
Write-Host "Services are launching in new CMD windows." -ForegroundColor Yellow
Write-Host "1. check the 'Java Event Service' window for startup logs."
Write-Host "2. check the 'Python Smart Gateway' window for startup logs."
Write-Host "3. Then run: python test_agent.py"
Write-Host "--------------------------------------------------------" -ForegroundColor Green

rootProject.name = "marketnote"

include("common")

include("user-service")
include("user-service:user-adapters")
include("user-service:user-application")
include("user-service:user-domain")

include("product-service")
include("product-service:product-adapters")
include("product-service:product-application")
include("product-service:product-domain")

include("commerce-service")
include("commerce-service:commerce-adapters")
include("commerce-service:commerce-application")
include("commerce-service:commerce-domain")

include("file-service")
include("file-service:file-adapters")
include("file-service:file-application")
include("file-service:file-domain")

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

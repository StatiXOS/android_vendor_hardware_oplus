#
# Copyright (C) 2022-2023 The LineageOS Project
#
# SPDX-License-Identifier: Apache-2.0
#

SEPOLICY_PLATFORM := $(subst device/qcom/sepolicy_vndr/,,$(SEPOLICY_PATH))

BOARD_VENDOR_SEPOLICY_DIRS += \
    vendor/hardware/oplus/sepolicy/qti/vendor \
    vendor/hardware/oplus/sepolicy/qti/vendor/$(SEPOLICY_PLATFORM)

SYSTEM_EXT_PRIVATE_SEPOLICY_DIRS += \
    vendor/hardware/oplus/sepolicy/qti/private \
    vendor/hardware/oplus/sepolicy/qti/private/$(SEPOLICY_PLATFORM)

SYSTEM_EXT_PUBLIC_SEPOLICY_DIRS += \
    vendor/hardware/oplus/sepolicy/qti/public \
    vendor/hardware/oplus/sepolicy/qti/public/$(SEPOLICY_PLATFORM)

ifneq ($(SEPOLICY_PLATFORM), legacy-um)
BOARD_VENDOR_SEPOLICY_DIRS += \
    vendor/hardware/oplus/sepolicy/qti/vendor/common-um

SYSTEM_EXT_PRIVATE_SEPOLICY_DIRS += \
    vendor/hardware/oplus/sepolicy/qti/private/common-um

SYSTEM_EXT_PUBLIC_SEPOLICY_DIRS += \
    vendor/hardware/oplus/sepolicy/qti/public/common-um
endif

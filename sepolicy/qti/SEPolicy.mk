#
# Copyright (C) 2022-2023 The LineageOS Project
#
# SPDX-License-Identifier: Apache-2.0
#
ifeq ($(SEPOLICY_PATH), device/qcom/sepolicy_vndr-legacy-um)
    SEPOLICY_PLATFORM := legacy-um
else
    SEPOLICY_PLATFORM := um
endif

BOARD_VENDOR_SEPOLICY_DIRS += \
    vendor/hardware/oplus/sepolicy/qti/vendor \
    vendor/hardware/oplus/sepolicy/qti/vendor/$(SEPOLICY_PLATFORM)

SYSTEM_EXT_PRIVATE_SEPOLICY_DIRS += \
    vendor/hardware/oplus/sepolicy/qti/private \
    vendor/hardware/oplus/sepolicy/qti/private/$(SEPOLICY_PLATFORM)

SYSTEM_EXT_PUBLIC_SEPOLICY_DIRS += \
    vendor/hardware/oplus/sepolicy/qti/public \
    vendor/hardware/oplus/sepolicy/qti/public/$(SEPOLICY_PLATFORM)

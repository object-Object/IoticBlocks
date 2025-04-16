from importlib.resources import Package
from typing import Any

from hexdoc.core import Properties
from hexdoc.plugin import (
    HookReturn,
    ModPlugin,
    ModPluginImpl,
    ModPluginWithBook,
    UpdateTemplateArgsImpl,
    hookimpl,
)
from hexdoc.utils import cast_or_raise
from typing_extensions import override

import hexdoc_ioticblocks

from .__gradle_version__ import FULL_VERSION, MINECRAFT_VERSION, MOD_ID, MOD_VERSION
from .__version__ import PY_VERSION


class IoticBlocksPlugin(ModPluginImpl, UpdateTemplateArgsImpl):
    @staticmethod
    @hookimpl
    def hexdoc_mod_plugin(branch: str) -> ModPlugin:
        return IoticBlocksModPlugin(branch=branch)

    @staticmethod
    @hookimpl
    def hexdoc_update_template_args(template_args: dict[str, Any]) -> None:
        props = cast_or_raise(template_args["props"], Properties)
        if props.modid == MOD_ID:
            template_args |= {
                "ioticblocks_api_docs_url": str(props.env.github_pages_url / "api"),
            }


class IoticBlocksModPlugin(ModPluginWithBook):
    @property
    @override
    def modid(self) -> str:
        return MOD_ID

    @property
    @override
    def full_version(self) -> str:
        return FULL_VERSION

    @property
    @override
    def mod_version(self) -> str:
        return f"{MOD_VERSION}+{MINECRAFT_VERSION}"

    @property
    @override
    def plugin_version(self) -> str:
        return PY_VERSION

    @override
    def resource_dirs(self) -> HookReturn[Package]:
        # lazy import because generated may not exist when this file is loaded
        # eg. when generating the contents of generated
        # so we only want to import it if we actually need it
        from ._export import generated

        return generated

    @override
    def jinja_template_root(self) -> tuple[Package, str]:
        return hexdoc_ioticblocks, "_templates"

# Nuvio Plus

Personal build of [NuvioTV](https://github.com/NuvioMedia/NuvioTV) with a few patches (autoplay of link-only
addon streams, sideload variant labelled "Nuvio Plus", updater pointing at this fork).

- `patches/`: `git format-patch` series applied with `git am --3way` on top of an upstream release tag.
- `.github/workflows/nuvio-plus.yml`: daily check of upstream releases. On a new one it applies the patches,
  runs the unit tests, builds `assembleFullDebug`, signs, and publishes `<upstreamTag>-plus` here.
- `juan/sideload`: the patched source branch the patches are generated from
  (`git format-patch <upstream tag>..juan/sideload`).

If a patch stops applying, the workflow fails and sends a Telegram message. Rebase `juan/sideload` on the new
upstream tag and regenerate `patches/`.

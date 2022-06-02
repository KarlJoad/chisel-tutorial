{ pkgs ? import <nixpkgs> {} }:

with pkgs.lib;

pkgs.mkShell {
  buildInputs = with pkgs; [
    sbt
    scala
    bloop # Build server

    # keep this line if you use bash
    bashInteractive
  ];
}

# Mini readme

## Genera pdf

```bash
inotifywait -m -e modify enunciado.tex --format '%w%f' | while read archivo; do
    echo "El archivo $archivo fue modificado. Ejecutando comando..."
    # Aquí va el comando que deseas ejecutar
    make enunciado.pdf
done
```
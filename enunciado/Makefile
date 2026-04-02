all: enunciado.pdf clean

enunciado.pdf: enunciado.tex
	latexmk --shell-escape -pdfxe enunciado.tex -output-directory=.

clean:
	latexmk -c
	rm -r *.log *.aux *.out *.toc *.fls *.fdb_latexmk *.bbl *.blg .*.swp
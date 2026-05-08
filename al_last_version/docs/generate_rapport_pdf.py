# -*- coding: utf-8 -*-
"""Genere le rapport PDF Minishop (architecture). Requiert: pip install reportlab pillow."""
from pathlib import Path

from PIL import Image as PILImage
from reportlab.lib import colors
from reportlab.lib.enums import TA_CENTER, TA_JUSTIFY
from reportlab.lib.pagesizes import A4
from reportlab.lib.styles import ParagraphStyle, getSampleStyleSheet
from reportlab.lib.units import cm
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont
from reportlab.platypus import (
    Image,
    PageBreak,
    Paragraph,
    SimpleDocTemplate,
    Spacer,
    Table,
    TableStyle,
)

BASE = Path(__file__).resolve().parent
OUT = BASE / "Rapport_Architecture_MiniShop.pdf"
IMG_OLD = BASE / "figures" / "architecture_ancienne.png"
IMG_NEW = BASE / "figures" / "architecture_nouvelle.png"


def _fit_image(path: str, max_width_cm: float = 16, max_height_cm: float = 9.5):
    """Insere l'image en respectant largeur max et hauteur max (page A4)."""
    max_w = max_width_cm * cm
    max_h = max_height_cm * cm
    with PILImage.open(path) as im:
        w, h = im.size
    scale = min(max_w / float(w), max_h / float(h))
    return Image(path, width=w * scale, height=h * scale)


def register_font():
    arial = r"C:\Windows\Fonts\arial.ttf"
    arialbd = r"C:\Windows\Fonts\arialbd.ttf"
    if Path(arial).is_file() and Path(arialbd).is_file():
        pdfmetrics.registerFont(TTFont("MiniArial", arial))
        pdfmetrics.registerFont(TTFont("MiniArialBold", arialbd))
        pdfmetrics.registerFontFamily(
            "MiniArial",
            normal="MiniArial",
            bold="MiniArialBold",
            italic="MiniArial",
            boldItalic="MiniArialBold",
        )
        return "MiniArial", "MiniArialBold"
    return "Helvetica", "Helvetica-Bold"


def build_story(font, font_bold):
    styles = getSampleStyleSheet()
    title = ParagraphStyle(
        "T",
        parent=styles["Heading1"],
        fontName=font,
        fontSize=16,
        alignment=TA_CENTER,
        spaceAfter=12,
    )
    h1 = ParagraphStyle(
        "H1",
        parent=styles["Heading1"],
        fontName=font,
        fontSize=14,
        spaceAfter=8,
        spaceBefore=14,
    )
    h2 = ParagraphStyle(
        "H2",
        parent=styles["Heading2"],
        fontName=font,
        fontSize=12,
        spaceAfter=6,
        spaceBefore=10,
    )
    body = ParagraphStyle(
        "B",
        parent=styles["Normal"],
        fontName=font,
        fontSize=10,
        leading=14,
        alignment=TA_JUSTIFY,
        spaceAfter=8,
    )
    small = ParagraphStyle(
        "S",
        parent=body,
        fontSize=9,
        textColor=colors.grey,
    )

    story = []

    story.append(Paragraph("Rapport technique — Architecture logicielle", title))
    story.append(Paragraph("Application MiniShop (Jakarta EE, NetBeans, GlassFish)", title))
    story.append(Spacer(1, 0.4 * cm))
    story.append(
        Paragraph(
            "<i>Document genere automatiquement — Comparaison ancienne vs nouvelle architecture.</i>",
            small,
        )
    )
    story.append(Spacer(1, 0.8 * cm))

    story.append(Paragraph("1. Contexte", h1))
    story.append(
        Paragraph(
            "MiniShop est une application e-commerce de demonstration basee sur Jakarta EE : "
            "couche presentation (JSP, servlet), logique metier (EJB stateless / stateful), "
            "acces aux donnees (DAO, JPA) et base SQL. "
            "Le projet a ete restructure pour introduire des <b>ports</b> (interfaces) "
            "entre la servlet et les EJB, dans une demarche proche de l'architecture hexagonale.",
            body,
        )
    )

    story.append(Paragraph("2. Limites de l'ancienne architecture", h1))
    story.append(
        Paragraph(
            "L'ancienne version suivait une organisation en couches classique ou la servlet "
            "connaissait directement les <b>implementations</b> nommees "
            "(MiniShopLess, MiniShopFull, OrderService). Les principales limites sont :",
            body,
        )
    )
    bullets_old = [
        "<b>Couplage fort presentation / metier :</b> la servlet importait les classes EJB concretes ; "
        "tout renommage ou remplacement d'un bean impactait la couche web.",
        "<b>Fuite d'information vers la presentation :</b> la servlet dependait des details techniques "
        "des beans (d'ou la mention « details connus par la servlet » sur le schema).",
        "<b>Commande liee au type concret du panier :</b> OrderService utilisait MiniShopFull "
        "comme type de parametre au lieu d'une abstraction « panier ».",
        "<b>Evolutivite et tests :</b> plus difficile de substituer une implementation ou de tester "
        "sans monter l'integralite des EJB.",
        "<b>Manque de contrat explicite :</b> pas d'interfaces nommees pour catalogue, panier et validation de commande.",
    ]
    for b in bullets_old:
        story.append(Paragraph(f"• {b}", body))
    story.append(Spacer(1, 0.3 * cm))
    story.append(Paragraph("<b>Figure 1 — Ancienne architecture (couches directes)</b>", h2))
    if IMG_OLD.is_file():
        story.append(_fit_image(str(IMG_OLD)))
    else:
        story.append(Paragraph("<i>(Image introuvable : figures/architecture_ancienne.png)</i>", body))
    story.append(PageBreak())

    story.append(Paragraph("3. Points forts de la nouvelle architecture", h1))
    story.append(
        Paragraph(
            "La nouvelle version introduit une couche <b>Ports — interfaces</b> "
            "(CatalogPort, CartPort, CheckoutPort) entre la presentation et les EJB. "
            "Les beans existants <b>implementent</b> ces interfaces ; la servlet n'injecte plus que les ports.",
            body,
        )
    )
    bullets_new = [
        "<b>Decouplage :</b> la servlet ne depend plus des noms de classes EJB, seulement des contrats.",
        "<b>Abstraction du panier :</b> CheckoutPort.confirmOrder(..., CartPort) exprime le metier sans lier a MiniShopFull.",
        "<b>Clarte des responsabilites :</b> trois ports alignes sur les cas d'usage (catalogue, panier, commande).",
        "<b>Alignement avec les DAO existants :</b> ProductDAO et OrderDAO restent les adaptateurs vers la persistance.",
            "<b>Verifiabilite :</b> les regles ArchUnit peuvent imposer que la presentation n'importe pas le package EJB.",
    ]
    for b in bullets_new:
        story.append(Paragraph(f"• {b}", body))
    story.append(Spacer(1, 0.3 * cm))
    story.append(Paragraph("<b>Figure 2 — Nouvelle architecture (ports et implementations EJB)</b>", h2))
    if IMG_NEW.is_file():
        story.append(_fit_image(str(IMG_NEW)))
    else:
        story.append(Paragraph("<i>(Image introuvable : figures/architecture_nouvelle.png)</i>", body))
    story.append(PageBreak())

    story.append(Paragraph("4. Pourquoi ce choix architectural ?", h1))
    story.append(
        Paragraph(
            "Ce choix equilibre <b>theorie</b> (hexagonale, inversion des dependances) et "
            "<b>pratique</b> de projet : pas de microservices ni de reecriture complete, "
            "mais des interfaces explicites et une servlet qui ne connait plus les implementations. "
            "C'est adapte a un enseignement Jakarta EE et facilite la maintenance et l'evolution future "
            "(par exemple exposition REST sur les memes ports).",
            body,
        )
    )

    story.append(Paragraph("5. Comparaison synthetique", h1))
    data = [
        ["Critere", "Ancienne architecture", "Nouvelle architecture"],
        [
            "Dependances de la servlet",
            "Classes EJB concretes",
            "Interfaces CatalogPort, CartPort, CheckoutPort",
        ],
        [
            "Couplage",
            "Fort (noms de beans dans la presentation)",
            "Reduit (contrats stables)",
        ],
        [
            "Commande / panier",
            "OrderService(MiniShopFull)",
            "CheckoutPort + CartPort",
        ],
        [
            "DAO / base",
            "Inchange",
            "Inchange",
        ],
        [
            "Tests & evolution",
            "Plus sensibles aux refactorings",
            "Interfaces plus faciles a mocker / substituer",
        ],
    ]
    t = Table(data, colWidths=[4 * cm, 6 * cm, 6 * cm])
    t.setStyle(
        TableStyle(
            [
                ("BACKGROUND", (0, 0), (-1, 0), colors.HexColor("#4472C4")),
                ("TEXTCOLOR", (0, 0), (-1, 0), colors.whitesmoke),
                ("FONTNAME", (0, 0), (-1, 0), font_bold),
                ("FONTNAME", (0, 1), (-1, -1), font),
                ("FONTSIZE", (0, 0), (-1, -1), 9),
                ("GRID", (0, 0), (-1, -1), 0.5, colors.grey),
                ("VALIGN", (0, 0), (-1, -1), "TOP"),
                ("ROWBACKGROUNDS", (0, 1), (-1, -1), [colors.white, colors.HexColor("#F2F2F2")]),
            ]
        )
    )
    story.append(t)
    story.append(Spacer(1, 0.6 * cm))
    story.append(
        Paragraph(
            "<b>Conclusion.</b> L'introduction des ports ne change pas le comportement fonctionnel "
            "ni la stack technique (GlassFish, JPA), mais ameliore la structure du code et prepare "
            "de futures extensions avec moins de friction.",
            body,
        )
    )

    return story


def main():
    font, font_bold = register_font()
    doc = SimpleDocTemplate(
        str(OUT),
        pagesize=A4,
        rightMargin=2 * cm,
        leftMargin=2 * cm,
        topMargin=2 * cm,
        bottomMargin=2 * cm,
        title="MiniShop — Rapport architecture",
        author="MiniShop",
    )
    doc.build(build_story(font, font_bold))
    print(f"PDF cree : {OUT}")


if __name__ == "__main__":
    main()
